(ns cannibal-corpse.extractor.util
  (:require [clojure.string :as string]
            [net.cgrand.enlive-html :as eh]))


(defn ->number [s]
  (when s
    (when-let [matched (re-find #"[\d.,]+" s)]
      (read-string
        (string/replace matched #"," "")))))


(defn ->content [elem]
  (when elem
    (-> elem first :content first)))


(defn select-any [html & selectors]
  (->> selectors
    (map #(eh/select html %))
    (remove empty?)
    first))
