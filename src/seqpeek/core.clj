(ns seqpeek.core
  (:use [clojure.core.match :only [match]]
        [clojure.string :only [lower-case]]
        seqpeek.count-reads
        seqpeek.filter-reads
        seqpeek.plot
        seqpeek.sample)
  (:gen-class))

(defn -main
  "The main entry point for seqpeek"
  [command & args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  ;; decipher the command and dispatch to the appropriate authority
  (match [(lower-case command)]
    ["count"]   (count-reads args)
    ["filter"]  (filter-reads args)
    ["plot"]    (plot args)
    ["sample"]  (sample args)
    [_]         (println (str "Unknown command: " command))))
