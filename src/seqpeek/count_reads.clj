(ns seqpeek.count-reads
  (:use [clojure.tools.cli :only [cli]]
        [seqpeek.command]
        [seqpeek.fastq :only [fastq-seq-over]]
        [seqpeek.file :only [make-line-seq]]))

(defn- parse-args
  "Argument parser for the count-reads command."
  [args]
  (cli args
       ["-n" "--min-length" "Filter reads by minimum length" :parse-fn #(Integer/parseInt %)]
       ["-m" "--max-length" "Filter reads by maximum length" :parse-fn #(Integer/parseInt %)]
       ["-h" "--help" "Display usage and quit" :flag true]))

(defn build-filter
  "Creates a filter predicate based on the provided options."
  [options]
  (let [[lb ub] [(:min-length options) (:max-length options)]]
    (cond (and (nil? ub) (nil? lb)) (fn [_] true)
          (nil? lb) (fn [x] (<= (count x) ub))
          (nil? ub) (fn [x] (>= (count x) lb))
          :else (fn [x] (and (<= (count x) ub) (>= (count x) lb))))))

(defn count-matching-reads
  "Counts reads in the sequence matching the provided filter"
  [pred coll]
  (count (filter pred coll)))

(defn- count-matching-reads-in-file
  "Counts reads in the input file matching the provided filter."
  [seqfilter filename]
  (let [rec (fastq-seq-over filename)
        sequences (map :seq rec)]
    (count-matching-reads seqfilter sequences)))

(defn- count-reads*
  "The body of the count-reads command."
  [options files body]
  (let [seqfilter (build-filter options)
          print-filenames (< 1 (count files))]
      (doseq [filename files]
        (when print-filenames
          (println (str filename ": "))
          (print "\t"))
        (println (count-matching-reads-in-file seqfilter filename)))))

(defn count-reads
  "The entry point for the count-reads command."
  [args]
  (command parse-args args count-reads*))
