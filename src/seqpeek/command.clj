(ns seqpeek.command
  (:use [clojure.tools.cli :only[cli]]))

(defn command
  "The standard command boilerplate."
  [parse-args args body]
  (let [[options files banner] (parse-args args)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (body options files banner)))
