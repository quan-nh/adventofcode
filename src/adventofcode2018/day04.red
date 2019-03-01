Red []

input: read/lines %../../resources/2018/day04

records: []

foreach line input [
  append/only records load line  
]

sort records

add-time: function [m g t] [
  either n: find m g [
    n/2: n/2 + t
  ] [
    repend m [g t]
  ]
]

add-item: function [m k v] [
  either val: select m k [
    append val v
  ] [
    append m k
    repend/only m [v]
  ]
]

guard-sleep: []
guard-time: make hash! []

foreach record records [
  switch record/2 [
    Guard [guard: record/3]
    falls [
      time-sleep: record/1/1 + record/1/2
      add-item guard-time guard time-sleep
    ]
    wakes [
      time-wake: record/1/1 + record/1/2
      add-time guard-sleep guard difference time-wake time-sleep
      add-item guard-time guard time-wake
    ]
  ]
]

most-sleep-time: 0:0
most-sleep-guard: none

foreach [guard time] guard-sleep [
  if time > most-sleep-time [
    most-sleep-time: time
    most-sleep-guard: guard]   
]

probe most-sleep-guard
probe most-sleep-time
probe select guard-time most-sleep-guard
