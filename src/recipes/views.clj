(ns recipes.views
  (:require [hiccup.core :refer [html]]
            [recipes.read :refer :all]
            [recipes.pages :refer [path-for]]))

(defmulti view (fn [[dispacher & _]] dispacher))

(defmethod view :index-page [_]
  [:ul
   (for [tag all-tags]
     [:li [:a {:href (path-for :recipes-page tag)} (name tag)]])])

(defmethod view :recipes-page [[_ tag]]
  (for [recipe (recipes-tag tag)]
    [:div [:h1 (:title recipe)]
     [:p (:description recipe)]
     [:h2 "Sources"]
     [:ul (for [source (:sources recipe)]
            [:li [:a {:href source} source]])]]))

(defn format [v] (html (view v)))
