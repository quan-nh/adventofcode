#include <iostream>
#include <string>
#include <fstream>
#include <streambuf>
#include <stack>

using namespace std;

bool isPair(char a, char b) {
  return abs(a - b) == 32;
}

int part1(string s) {
  stack<char> st;

  int i=0, count = 0;
  while(s[i] != '\n'){
    if(st.empty() || !isPair(s[i], st.top())) {
      st.push(s[i]);
      count++;
    } else {
      st.pop();
      count--;
    }
    i++;
  }

  return count;
}

int part2(string s, char ch) {
  stack<char> st;

  int i=0, count = 0;
  while(s[i] != '\n'){
    if(s[i] == ch || s[i] == ch - 32) {
      i++; continue;
    }
    
    if(st.empty() || !isPair(s[i], st.top())) {
      st.push(s[i]);
      count++;
    } else {
      st.pop();
      count--;
    }
    i++;
  }

  return count;
}

int main() {
  ifstream f("resources/2018/day05");
  string s((istreambuf_iterator<char>(f)), istreambuf_iterator<char>());

  cout << part1(s) << '\n';

  int shortest = INT_MAX;
  for(char c = 'a'; c <= 'z'; ++c) {
    int count = part2(s, c);
    if(count < shortest) shortest = count;
  }
  cout << shortest;
}    
