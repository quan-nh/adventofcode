Red []

comment {
  The characters represent groups - sequences that begin with { and end with }.
  Within a group, there are zero or more other things, separated by commas: either another group or garbage.
  Garbage begins with < and ends with >. Between those angle brackets, almost any character can appear, including { and }. Within garbage, < has no special meaning.
  Inside garbage, any character that comes after ! should be ignored, including <, >, and even another !.
  Your puzzle input represents a single, large group which itself contains many smaller ones.
}

group:   ["{" any [group | "," | garbage] "}"]
garbage: ["<" any [chars | ignored] ">"]
chars:   charset [not "!>"]
ignored: ["!" skip]

parse "<>" garbage
parse "<random characters>" garbage
parse "<<<<>" garbage
parse "<{!>}>" garbage
parse "<!!>" garbage
parse "<!!!>>" garbage
parse "<{o^"i!a,<{i<a>" garbage

parse "{}" group
parse "{{{}}}" group
parse "{{},{}}" group
parse "{{{},{},{{}}}}" group
parse "{<{},{},{{}}>}" group
parse "{<a>,<a>,<a>,<a>}" group
parse "{{<a>},{<a>},{<a>},{<a>}}" group
parse "{{<!>},{<!>},{<!>},{<a>}}" group
