(ns ankilympics.core
  (:use [noir.server :only [start]]
        [clojure.data.json :only [write-str]]
        noir.core))

(def points (ref {}))

(def rivals (ref {}))

(defpage [:post "/points"] {:keys [name amount]}
  (dosync
   (alter points assoc name amount))
  (write-str {:msg (str @points)}))

(defpage "/" []
  (str @points))

(defn -main [port]
  (start (Integer. port)))

