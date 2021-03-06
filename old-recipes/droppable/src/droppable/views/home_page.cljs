(ns droppable.views.home-page
  (:require [reagent.core :as reagent]
            [droppable.session :as session :refer [global-put! global-state]]))

(global-put! :drops 0)

(defn home-render []
  [:div
   [:h2 "Home Page"]
   [:div "The total number of drops has been: " [:span#total-drops (global-state :drops) ]]
   [:div#draggable.ui-widget-content [:p "Drag me to my target"]]
   [:div#droppable.ui-widget-header [:p "Drop here"]]
   ])

(defn home-did-mount []
  (js/$ (fn []
          (.draggable (js/$ "#draggable"))
          (.droppable (js/$ "#droppable")
                      #js {:drop (fn [event ui]
                                   (this-as this
                                            (.html (.find (.addClass (js/$ this) "ui-state-highlight") 
                                                          "p")
                                                   "Dropped!"))
                                   (global-put! :drops (inc (global-state :drops)))
                                   )}))))

(defn home-page []
  (reagent/create-class {:render home-render
                         :component-did-mount home-did-mount}))
