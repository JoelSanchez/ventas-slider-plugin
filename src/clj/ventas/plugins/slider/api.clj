(ns ventas.plugins.slider.api
  (:require
   [ventas.server.api :as api]
   [ventas.database.entity :as entity]
   [slingshot.slingshot :refer [throw+]]))

(api/register-endpoint!
 ::sliders.get
 (fn [{:keys [params]} {:keys [session]}]
   (let [{:keys [keyword]} params]
     (if-let [slider (entity/find [:slider.slider/keyword keyword])]
       (api/serialize-with-session session slider)
       (throw+ {:type ::slider-not-found
                :keyword keyword})))))
