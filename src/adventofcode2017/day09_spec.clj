(ns adventofcode2017.day09-spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::garbage (s/cat
                   :begin #{\<}
                   :content (s/*
                              (s/alt
                                :chars (s/+ (comp not #{\! \>}))
                                :ignored (s/cat :ign #{\!}
                                                :char char?)
                                ))
                   :end #{\>}))

(s/conform ::garbage (seq "<>"))
(s/conform ::garbage (seq "<random characters>"))
(s/conform ::garbage (seq "<<<<>"))
(s/conform ::garbage (seq "<{!>}>"))
(s/conform ::garbage (seq "<!!>"))
(s/conform ::garbage (seq "<!!!>>"))
(s/conform ::garbage (seq "<{o\"i!a,<{i<a>"))


(s/def ::stuff (s/alt :group ::group
                      :garbage ::garbage))

(s/def ::group (s/cat
                 :begin #{\{}
                 :content (s/?
                            (s/cat
                              :stuff ::stuff
                              :more (s/*
                                      (s/cat
                                        :separated #{\,}
                                        :stuff ::stuff))))
                 :end #{\}}))

(s/conform ::group (seq "{}"))
(s/conform ::group (seq "{{{}}}"))
(s/conform ::group (seq "{{},{}}"))
(s/conform ::group (seq "{{{},{},{{}}}}"))
(s/conform ::group (seq "{<{},{},{{}}>}"))
(s/conform ::group (seq "{<a>,<a>,<a>,<a>}"))
(s/conform ::group (seq "{{<a>},{<a>},{<a>},{<a>}}"))
(s/conform ::group (seq "{{<!>},{<!>},{<!>},{<a>}}"))
