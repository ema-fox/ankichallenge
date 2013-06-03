(ns ankichallenge.core
  (:use [noir.server :only [start]]
        [clojure.data.json :only [write-str]]
        noir.core
        hiccup.core))

(defonce data (ref {:points {}
                    :init-points {}}))

(defn show-points []
  (str (:points @data)))

(defpage [:post "/points"] {:keys [name amount]}
  (let [amount (Integer. amount)]
    (dosync
     (alter data update-in [:init-points name] #(or % amount))
     (alter data assoc-in [:points name] amount))
    (write-str {:msg (show-points)})))

(defpage "/" []
  (html
   [:h1 "Ankichallenge high score"]
   [:div (for [[name points]
               (sort-by second >
                        (merge-with - (:points @data) (:init-points @data)))]
           [:div (str name ": " points)])]))

(defn -main [port]
  (start (Integer. port)))

