(ns seqpeek.count-reads-test
  (:require [speclj.core :refer :all]
            [seqpeek.count-reads :refer :all]))

(describe "build-filter"
  (it "should not filter when given no criteria"
    (let [seq-filter (build-filter {})]
      (should (seq-filter ""))
      (should (seq-filter "A"))
      (should (seq-filter "AAAAAGGGGGGGTTT"))))
  (it "should filter sequences that are shorter than the minimum length"
    (let [seq-filter (build-filter {:min-length 8})]
      (should-not (seq-filter ""))
      (should-not (seq-filter "A"))
      (should-not (seq-filter "CTGTACC"))
      (should (seq-filter "CTcGAGGG"))
      (should (seq-filter "CCGGCTCGAGTTTTTTG"))))
  (it "should filter sequences that are longer than the maximum length"
    (let [seq-filter (build-filter {:max-length 6})]
      (should (seq-filter ""))
      (should (seq-filter "CCGG"))
      (should (seq-filter "CTCGAG"))
      (should-not (seq-filter "GCTCGAGC"))))
  (it "should filter sequences that are within a range"
    (let [seq-filter (build-filter {:min-length 1 :max-length 4})]
      (should-not (seq-filter ""))
      (should (seq-filter "G"))
      (should (seq-filter "CCGG"))
      (should-not (seq-filter "CTCGA")))))

(run-specs)