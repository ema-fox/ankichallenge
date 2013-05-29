(ns ankilympics.core
  (:use [noir.server :only [start]]
        [clojure.data.json :only [write-str]]
        noir.core))

(defonce data (ref {:points {}}))

(defpage [:post "/points"] {:keys [name amount]}
  (dosync
   (alter data assoc-in [:points name] amount))
  (write-str {:msg (str @data)}))

(defpage "/" []
  (str @data))

(defn -main [port]
  (start (Integer. port)))

