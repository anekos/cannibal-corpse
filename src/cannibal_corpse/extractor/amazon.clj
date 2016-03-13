(ns cannibal-corpse.extractor.amazon
  (:require [cannibal-corpse.extractor.util :refer :all]
            [net.cgrand.enlive-html :as eh]))


(def selectors
  {:kindle [:#kindle_meta_binding_winner :td.price]})


(defn extract-price [html]
  (-> html
    (select-any ; Kindle 乱歩 http://www.amazon.co.jp/dp/B010D4N3XI
                [:#kindle_meta_binding_winner :span.price]
                ; ソルベント http://www.amazon.co.jp/gp/product/B006JHAQ70
                ; 机 http://amazon.jp/o/ASIN/B000UGVTEK
                [:#priceblock_ourprice])
    ->content
    ->number))
