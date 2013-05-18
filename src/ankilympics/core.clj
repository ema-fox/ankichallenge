(ns ankilympics.core
  (:use [noir.server :only [start]]
        noir.core))

(def points (ref {}))

(def rivals (ref {}))

(defpage [:post "/points"] {:keys [name amount]}
  (dosync
   (alter points assoc name amount))
  (str @points))

(defpage "/" []
  "Huhu!")

(defn -main [port]
  (start (Integer. port)))

