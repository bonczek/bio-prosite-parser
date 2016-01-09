package pl.gda.eti.pg.prosite;

import org.hamcrest.Matcher;
import org.testng.annotations.Test;
import pl.gda.eti.pg.prosite.state.State;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternParserTest {

    private PatternParser patternParser = new PatternParser();

    @Test
    public void testParse() throws Exception {
        String pattern = "[ABC]-[DEF]-[GHI]";
        State res = patternParser.parse(pattern);

        assertThat(res, notNullValue());
        assertThat(res.isFinal(), is(false));
        assertNextValues(res, Arrays.asList('A', 'B', 'C'), notNullValue());

        State secondState = res.next('A');
        assertNextValues(secondState, Arrays.asList('D', 'E', 'F'), notNullValue());

        State thirdState = secondState.next('D');
        assertNextValues(thirdState, Arrays.asList('G', 'H', 'I'), notNullValue());
        assertThat(thirdState.next('G').isFinal(), is(true));
    }

    @Test
    public void testParseWithNoneOf() throws Exception {
        String pattern = "{ABC}-{DEF}";
        State res = patternParser.parse(pattern);

        assertThat(res, notNullValue());
        assertThat(res.isFinal(), is(false));

        assertNextValues(res, Arrays.asList('A', 'B', 'C'), nullValue());

        State secondState = res.next('D');
        assertThat(secondState, notNullValue());
        assertNextValues(secondState, Arrays.asList('D', 'E', 'F'), nullValue());

        State thirdState = secondState.next('G');
        assertThat(thirdState.isFinal(), is(true));
    }

    @Test
    public void testParseAnythingState() throws Exception {
        String pattern = "x-x";
        State res = patternParser.parse(pattern);

        assertThat(res, notNullValue());
        State secondState = res.next('F');
        assertThat(secondState, notNullValue());
        State thirdState = secondState.next('Z');
        assertThat(thirdState, notNullValue());
        assertThat(thirdState.isFinal(), is(true));
    }

    @Test
    public void testParseSingleCharacter() throws Exception {
        String pattern = "F-G-H";
        State state = patternParser.parse(pattern);

        assertThat(state, notNullValue());
        assertThat(state.next('X'), nullValue());
        State secondState = state.next('F');
        assertThat(secondState, notNullValue());
        State thirdState = secondState.next('G');
        assertThat(thirdState, notNullValue());
        State fourthState = thirdState.next('H');
        assertThat(fourthState, notNullValue());
        assertThat(fourthState.isFinal(), is(true));
    }

    @Test
    public void testExactlyKTimes() throws Exception {
        String pattern = "A(2)-B(3)";
        State state = patternParser.parse(pattern);

        State s1 = state.next('A');
        assertThat(s1.next('x'), nullValue());
        State s2 = s1.next('A');
        State s3 = s2.next('B');
        State s4 = s3.next('B');
        assertThat(s3.next('Z'), nullValue());
        State s5 = s4.next('B');
        assertThat(s5.isFinal(), is(true));
    }

    private void assertNextValues(State state, List<Character> chars, Matcher<Object> matcher) {
        for (char c : chars) {
            assertThat(state.next(c), matcher);
        }
    }
}