(ns ventas.plugins.slider.api
  (:require [re-frame.core])
  (:require-macros
   [ventas.plugins.slider.api]
   [ventas.server.api.core :refer [define-api-events-for-ns!]]))

(define-api-events-for-ns!)
