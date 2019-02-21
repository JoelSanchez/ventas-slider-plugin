(ns ventas.plugins.slider.core
  (:require
   [re-frame.core :as rf]
   [ventas.common.utils :as common.utils]
   [ventas.components.base :as base]
   [ventas.components.slider :as components.slider]
   [ventas.plugins.slider.api :as slider.backend]))

(def state-key ::state)

(defn- reset-sliders [sliders]
  (->> sliders
       (common.utils/map-vals
        (fn [slider]
          (-> slider
              (assoc :current-index 0)
              (assoc :render-index 0))))))

(rf/reg-event-fx
 ::sliders.get
 (fn [_ [_ kw]]
   {:dispatch [::slider.backend/sliders.get
               {:params {:keyword kw}
                :success [::sliders.get.next kw]}]}))

(rf/reg-event-fx
 ::sliders.get.next
 (fn [{:keys [db]} [_ kw {:keys [slides auto-speed auto keyword]}]]
   (let [state-path [state-key kw]]
     {:db (assoc-in db state-path
                    {:slides (mapv (fn [image]
                                     (merge image
                                            {:width ::components.slider/viewport
                                             :height 350}))
                                   slides)
                     :orientation :horizontal
                     :render-index (dec (count slides))
                     :current-index 1
                     :visible-slides 1
                     :auto auto
                     :auto-speed auto-speed
                     :keyword keyword})
      :set-interval (when auto
                      {:id kw
                       :speed auto-speed
                       :callback #(rf/dispatch [::components.slider/next state-path])})})))

(defn slider* [keyword]
  (let [state-path [state-key keyword]]
    (fn [{:keys [slides]}]
      ^{:key @(rf/subscribe [:db (conj state-path :render-index)])}
      [:div.slider
       [:div.slider__slides {:style {:left @(rf/subscribe [::components.slider/offset state-path])}}
        (map-indexed
         (fn [idx {:keys [file]}]
           ^{:key idx}
           [:div.slider__slide {:style {:background-image (str "url(" (:url file) ")")}}])
         @(rf/subscribe [::components.slider/slides state-path]))]
       (when (pos? (count slides))
         [:div
          [:button.slider__left {:on-click #(rf/dispatch [::components.slider/previous state-path])}
           [base/icon {:name "chevron left"}]]
          [:button.slider__right {:on-click #(rf/dispatch [::components.slider/next state-path])}
           [base/icon {:name "chevron right"}]]])])))

(defn slider [kw]
  (let [slider-data @(rf/subscribe [:db [state-key kw]])]
    (when-let [kw (:keyword slider-data)]
      [slider* kw])))
