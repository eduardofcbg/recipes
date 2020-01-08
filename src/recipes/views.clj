(ns recipes.views
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css include-js]]
            [recipes.read :refer [public all-tags recipes-tag]]
            [recipes.pages :refer [path-for]]))

(defn- layout [& contents]
  (html5 {:lang "en"}
         [:head [:title "Recipes"]
          (include-css (path-for :public "main.css"))
          (include-js (path-for :public "script.js"))]
         [:body contents]))

(defmulti view (fn [[view-id & _]] view-id))

(defmethod view :public [[_ name]]
  (get (public) name))

(defmethod view :index [_]
  (layout [:h1 "Recipes"]
          [:ul (for [tag (all-tags)]
                 [:li [:a {:href (path-for :recipes tag)} (name tag)]])]))

(defn- recipe [{title :title description :description sources :sources}]
  [:div [:h2 title]
   [:p description]
   [:ul (for [source sources]
          [:li [:a {:class :source :href source} source]])]])

(defmethod view :recipes [[_ tag]]
  (layout [:h1 "Recipes: " (name tag)]
          (map recipe (recipes-tag tag))))
