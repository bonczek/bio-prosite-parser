package pl.gda.eti.pg.prosite;

import org.hamcrest.Matcher;
import org.testng.annotations.Test;
import pl.gda.eti.pg.prosite.rule.BetweenKAndJRule;
import pl.gda.eti.pg.prosite.rule.FinalRule;
import pl.gda.eti.pg.prosite.rule.Rule;
import pl.gda.eti.pg.prosite.rule.SingleCharacterRule;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternParserTest {

    private PatternParser patternParser = new PatternParser();

    @Test
    public void testParseOneOfRule() throws Exception {
        String pattern = "[ABC]-[DEF]-[GHI]";
        List<Rule> rulesChain = patternParser.parse(pattern);

        assertThat(rulesChain.size(), is(4));
        assertThat(rulesChain.get(0).isFinal(), is(false));
        assertNextValues(rulesChain.get(0), Arrays.asList('A', 'B', 'C'), notNullValue());

        Rule secondRule = rulesChain.get(0).next('A');
        assertNextValues(secondRule, Arrays.asList('D', 'E', 'F'), notNullValue());

        Rule thirdRule = secondRule.next('D');
        assertNextValues(thirdRule, Arrays.asList('G', 'H', 'I'), notNullValue());
        assertThat(thirdRule.next('G').isFinal(), is(true));
    }

    @Test
    public void testParseNoneOf() throws Exception {
        String pattern = "{ABC}-{DEF}";
        List<Rule> rulesChain = patternParser.parse(pattern);

        assertThat(rulesChain.size(), is(3));
        assertThat(rulesChain.get(0).isFinal(), is(false));

        assertNextValues(rulesChain.get(0), Arrays.asList('A', 'B', 'C'), nullValue());

        Rule secondRule = rulesChain.get(0).next('D');
        assertThat(secondRule, notNullValue());
        assertNextValues(secondRule, Arrays.asList('D', 'E', 'F'), nullValue());

        Rule thirdRule = secondRule.next('G');
        assertThat(thirdRule.isFinal(), is(true));
    }

    @Test
    public void testParseAnything() throws Exception {
        String pattern = "x-x";
        List<Rule> rulesChain = patternParser.parse(pattern);

        assertThat(rulesChain.size(), is(3));
        Rule secondRule = rulesChain.get(0).next('F');
        assertThat(secondRule, notNullValue());
        Rule thirdRule = secondRule.next('Z');
        assertThat(thirdRule, notNullValue());
        assertThat(thirdRule.isFinal(), is(true));
    }

    @Test
    public void testParseSingleCharacter() throws Exception {
        String pattern = "F-G-H";
        List<Rule> rulesChain = patternParser.parse(pattern);

        assertThat(rulesChain.size(), is(4));
        assertThat(rulesChain.get(0).next('X'), nullValue());
        Rule secondRule = rulesChain.get(0).next('F');
        assertThat(secondRule, notNullValue());
        Rule thirdRule = secondRule.next('G');
        assertThat(thirdRule, notNullValue());
        Rule fourthRule = thirdRule.next('H');
        assertThat(fourthRule, notNullValue());
        assertThat(fourthRule.isFinal(), is(true));
    }

    @Test
    public void testExactlyKTimes() throws Exception {
        String pattern = "A(2)-B(3)-x(3)";
        List<Rule> rulesChain = patternParser.parse(pattern);

        Rule s1 = rulesChain.get(0).next('A');
        assertThat(s1.next('x'), nullValue());
        Rule s2 = s1.next('A');
        Rule s3 = s2.next('B');
        Rule s4 = s3.next('B');
        assertThat(s3.next('Z'), nullValue());
        Rule s5 = s4.next('B');
        Rule s6 = s5.next('J');
        Rule s7 = s6.next('U');
        Rule s8 = s7.next('X');
        assertThat(s8.isFinal(), is(true));
    }

    @Test
    public void testBetweenKAndJ() throws Exception {
        String pattern = "e(2,3)-e-x(2,3)";
        List<Rule> rulesChain = patternParser.parse(pattern);

        BetweenKAndJRule r1 = (BetweenKAndJRule) rulesChain.get(0).next('e');
        assertThat(r1.getRuleAfter(), nullValue());
        BetweenKAndJRule r2 = (BetweenKAndJRule) r1.next('e');
        assertThat(r2.getRuleAfter(), notNullValue());
        assertThat(r2.getRuleAfter() instanceof SingleCharacterRule, is(true));
        Rule r3 = r2.next('e');
        assertThat(r3 instanceof SingleCharacterRule, is(true));
        Rule r4 = r3.next('e');
        Rule r5 = r4.next('I');
        BetweenKAndJRule r6 = (BetweenKAndJRule) r4.next('Z');
        assertThat(r6.getRuleAfter(), notNullValue());
        assertThat(r6.getRuleAfter() instanceof FinalRule, is(true));
        Rule r7 = r5.next('T');
        assertThat(r7, notNullValue());
        assertThat(r7 instanceof FinalRule, is(true));
    }

    private void assertNextValues(Rule rule, List<Character> chars, Matcher<Object> matcher) {
        for (char c : chars) {
            assertThat(rule.next(c), matcher);
        }
    }
}