(ns seqpeek.seq)

(defn count-long-reads
  "Returns the number of reads in coll that are longer than n bases."
  [coll n]
  (count 
   (filter #(<= n (count %)) coll)))

