class Solution {
 public:
  int numDupDigitsAtMostN(int n) {
    return n - countSpecialNumbers(n);
  }

 private:
  // Same as 2376. Count Special Integers
  int countSpecialNumbers(int n) {
    const int digitSize = log10(n) + 1;
    // dp[i][j][k] := the number of special integers, considering the i-th
    // digit, where `j` is a bitmask representing the digits that have been
    // used, and `k` indicates if the current digit is tightly bound
    dp.resize(digitSize + 1, vector<vector<int>>(1 << 10, vector<int>(2, -1)));
    return count(to_string(n), 0, 0, true) - 1;  // - 0;
  }

 private:
  vector<vector<vector<int>>> dp;

  int count(const string& s, int i, int used, bool isTight) {
    if (i == s.length())
      return 1;
    if (dp[i][used][isTight] != -1)
      return dp[i][used][isTight];

    int res = 0;

    const int maxDigit = isTight ? s[i] - '0' : 9;
    for (int d = 0; d <= maxDigit; ++d) {
      // `d` is used.
      if (used >> d & 1)
        continue;
      // Use `d` now.
      const bool nextIsTight = isTight && (d == maxDigit);
      if (used == 0 && d == 0)  // Don't count leading 0s as used.
        res += count(s, i + 1, used, nextIsTight);
      else
        res += count(s, i + 1, used | 1 << d, nextIsTight);
    }

    return dp[i][used][isTight] = res;
  }
};
