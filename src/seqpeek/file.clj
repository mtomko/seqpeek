(ns seqpeek.file
  (import (java.io BufferedReader File FileReader)))

(defn parent-of
  [file]
  (.getParentFile file))
