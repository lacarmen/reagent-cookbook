(ns adding-a-page.routes
    (:require [secretary.core :as secretary :include-macros true :refer [defroute]]
              [adding-a-page.session :as session :refer [global-put!]]
              [adding-a-page.views.pages :refer [pages]]
              [goog.events :as events]
              [goog.history.EventType :as EventType])
    (:import goog.History))

;; ----------
;; History
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))
;; need to run this after routes have been defined

;; ----------
;; Routes
(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (global-put! :current-page (pages :home-page))
    (global-put! :nav "home"))

  (defroute "/about" []
    (global-put! :current-page (pages :about-page))
    (global-put! :nav "about"))

  (defroute "/new" []
    (global-put! :current-page (pages :new-page))
    (global-put! :nav "new"))

  (hook-browser-navigation!)
  )
