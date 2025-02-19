class Solution {
  public int countSteppingNumbers(String low, String high) {
    final String lowWithLeadingZeros =
        String.valueOf('0').repeat(high.length() - low.length()) + low;
    // dp[i][j][k1][k2] := the number of valid integers, considering the i-th
    // digit, where `j` is the previous digit, `k1` indicates if the current
    // digit is tightly bound for `low`, and `k2` indicates if the current digit
    // is tightly bound for `high`
    dp = new Integer[high.length()][11][2][2];
    return count(lowWithLeadingZeros, high, 0, 10, /*isLeadingZero=*/true, true, true);
  }

  private static final int kMod = 1_000_000_007;
  private Integer[][][][] dp;

  private int count(final String low, final String high, int i, int prevDigit,
                    boolean isLeadingZero, boolean isTight1, boolean isTight2) {
    if (i == high.length())
      return 1;
    if (dp[i][prevDigit][isTight1 ? 1 : 0][isTight2 ? 1 : 0] != null)
      return dp[i][prevDigit][isTight1 ? 1 : 0][isTight2 ? 1 : 0];

    int res = 0;
    final int minDigit = isTight1 ? low.charAt(i) - '0' : 0;
    final int maxDigit = isTight2 ? high.charAt(i) - '0' : 9;

    for (int d = minDigit; d <= maxDigit; ++d) {
      final boolean nextIsTight1 = isTight1 && (d == minDigit);
      final boolean nextIsTight2 = isTight2 && (d == maxDigit);
      if (isLeadingZero)
        // Can place any digit in [minDigit, maxDigit].
        res += count(low, high, i + 1, d, isLeadingZero && d == 0, nextIsTight1, nextIsTight2);
      else if (Math.abs(d - prevDigit) == 1)
        // Can only place prevDigit - 1 or prevDigit + 1.
        res += count(low, high, i + 1, d, false, nextIsTight1, nextIsTight2);
      res %= kMod;
    }

    return dp[i][prevDigit][isTight1 ? 1 : 0][isTight2 ? 1 : 0] = res;
  }
}
