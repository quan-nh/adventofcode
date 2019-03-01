(def votes ["Laura" "Emily" "Louise" "Natasha" "Emily" "Lilly" "Louise" "Laura" "Mary" "Mary"])

(def t @{})
(var winner "")
(var max-vote 0)

(loop [girl :in votes]
  (if (t girl)
    (update t girl + 1)
    (put t girl 1))

  (cond
    (< max-vote (t girl))
    (do
      (set winner girl)
      (set max-vote (t girl)))
      
    (= max-vote (t girl))
    (set winner (max-order winner girl))))

(pp winner)
