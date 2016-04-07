(ns cannibal-corpse.util
  (:require [clojure.string :as string]))


(defn- key-to-getter [key]
  (symbol
    (str
      ".get"
      (string/replace (string/capitalize (name key))
                      #"-(\w+)"
                      #(string/capitalize (apply str (next %)))))))


(defmacro java-object-to-hashmap
  "(java-object-to-hashmap obj foo bar)
  equals
   {:foo (.getFoo obj)
    :bar (.getBar obj)}"
  [obj & keys]
  `(apply
     merge
     {}
     ~@(mapv (fn [key] {(keyword key) (list (key-to-getter key) obj)})
             keys)))
