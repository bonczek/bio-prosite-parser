package pl.gda.eti.pg.prosite;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternParserTest {

    private PatternParser patternParser = new PatternParser();

    @Test
    public void testParse() throws Exception {
        String pattern = "[ABC]-[DEF]-[GHI]";
        State res = patternParser.parse(pattern);

        assertThat(res, notNullValue());
        assertThat(res.isFinal(), is(false));

        assertThat(res.next('A'), notNullValue());
        assertThat(res.next('B'), notNullValue());
        assertThat(res.next('C'), notNullValue());

        State secondState = res.next('A');

        assertThat(secondState.next('D'), notNullValue());
        assertThat(secondState.next('E'), notNullValue());
        assertThat(secondState.next('F'), notNullValue());

        State thirdState = secondState.next('D');

        assertThat(thirdState.next('G'), notNullValue());
        assertThat(thirdState.next('H'), notNullValue());
        assertThat(thirdState.next('I'), notNullValue());

        assertThat(thirdState.next('G').isFinal(), is(true));

    }
}