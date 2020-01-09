(ns recipes.views
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css]]
            [recipes.read :refer [styles all-tags recipes-tag]]))

(defn- layout [path-for & contents]
  (html5 {:lang "en"}
         [:head [:title "Recipes"]
          (include-css (path-for :style "main.css"))]
         [:body contents]))

(defn- recipe [{title :title description :description sources :sources}]
  [:div [:h2 title]
   [:p description]
   [:ul (for [source sources]
          [:li [:a {:class :source :href source} source]])]])

(defmulti view (fn [[view-key & view-args] path-for] view-key))

(defmethod view :style [[_ name] _]
  (get (styles) name))

(defmethod view :index [_ path-for]
  (layout path-for
          [:h1 "Recipes"]
          [:ul (for [tag (all-tags)]
                 [:li [:a {:href (path-for :recipes tag)} (name tag)]])]))

(defmethod view :recipes [[_ tag] path-for]
  (layout path-for [:h1 "Recipes: " (name tag)]
          (map recipe (recipes-tag tag))))
