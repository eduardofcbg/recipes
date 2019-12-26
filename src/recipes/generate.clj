(ns recipes.generate)

(defmacro for-merge [seq-exprs body-expr]
  `(apply merge (for ~seq-exprs ~body-expr)))

(defn generate [folder website formater]
  (for-merge [[name content] (seq website)
              :let [path (str folder "/" name)]]
             (if (map? content)
               (generate path content formater)
               {path (formater content)})))
