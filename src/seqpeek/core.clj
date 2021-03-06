(ns seqpeek.core
  (:use [clojure.string :only [lower-case]]
        seqpeek.count-reads
        seqpeek.plot
        seqpeek.sample)
  (:gen-class)) 

(defn -main
  "The main entry point for seqpeek"
  [command & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; decipher the command and dispatch to the appropriate authority
  (case (lower-case command)
    "count" (count-reads args)
    "plot"  (plot args)
    "sample" (sample args)
    (println (str "Unknown command: " command))))
