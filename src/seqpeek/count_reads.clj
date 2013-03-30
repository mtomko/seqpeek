(ns seqpeek.count-reads
  (:use [clojure.tools.cli :only[cli]]
        [incanter core io stats charts]
        [seqpeek.fastq :only [fastq-seq-over]]
        [seqpeek.file :only [make-line-seq]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer/parseInt %)]
       ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer/parseInt %)]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn- build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (let [[lb ub] [(:min-length options) (:max-length options)]]
    (cond (and (nil? ub) (nil? lb)) (fn [_] true)
          (nil? lb) (fn [x] (< (count x) ub))
          (nil? ub) (fn [x] (>= (count x) lb))
          :else (fn [x] (and (< (count x) ub) (>= (count x) lb))))))

(defn- count-reads-for-file
  "Counts reads in the input file matching the provided filter."
  [filename seqfilter]
  (let [rec (fastq-seq-over filename)
        sequences (map :seq rec)]
    (count (filter seqfilter sequences))))

(defn count-reads
  "The entry point for the count-reads command."
  [args]
  (let [[options files banner] (parse-args args)]
    (when (:help options)
      (println banner)
      (System/exit 0))
    (let [seqfilter (build-filter options)
          print-filenames (<= 1 (count files))]
      (doseq [filename files]
        (when print-filenames
          (println (str filename ": "))
          (print "\t"))
        (println (count-reads-for-file filename seqfilter))))))

