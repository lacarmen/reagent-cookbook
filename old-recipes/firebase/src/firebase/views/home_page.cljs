(ns firebase.views.home-page
  (:require [firebase.session :as session :refer [global-state global-put!]]))

(defn on-change [event fb] 
  (.set fb (clj->js {:text-from-app (-> event .-target .-value)}))
  (.on fb "value" (fn [snapshot] 
                    (global-put! :my-text ((js->clj (.val snapshot)) "text-from-app")))))

(defn input-field [value fb]
  [:input {:type "text"
           :value value
           :on-change #(on-change % fb)}])

(defn home-page []
  (let [fb (js/Firebase. "FIXME")]  ;; REPLACE FIXME WITH YOUR FIREBASE REFERENCE
    [:div
     [:h2 "Home Page"]
     [:div "Woot! You are starting a reagent application."]

     [:div
      [:p "The value is now: " (global-state :my-text)]
      [:p "Change it here: " [input-field (global-state :my-text) fb]]]
     ]))
