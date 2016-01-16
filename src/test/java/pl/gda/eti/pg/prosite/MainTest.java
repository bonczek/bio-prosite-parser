package pl.gda.eti.pg.prosite;

import org.testng.annotations.Test;

public class MainTest {

    @Test
    public void testMain_givenLecturePatternAndSequence() throws Exception {
        String pattern = "[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]";

        String lectureString1 = "SRSLKMRGQAFVIFKEVSSAT";
        String lectureString2 = "KLTGRPRGVAFVRYNKREEAQ";
        String lectureString3 = "VGCSVHKGFAFVQYVNERNAR";

        System.out.println("Pierwsza sekwencja");
        String[] firstExecutionParams = {pattern, lectureString1};
        Main.main(firstExecutionParams);

        System.out.println("Druga sekwencja");
        String[] secondExecutionParams = {pattern, lectureString2};
        Main.main(secondExecutionParams);

        System.out.println("Trzecia sekwencja");
        String[] thirdExecutionParams = {pattern, lectureString3};
        Main.main(thirdExecutionParams);
    }

    @Test
    public void testMain_givenEvilPattern() throws Exception {
        String[] evilParams = {"e(2,5)-e(2,4)-e(2)-e", "eeeeeeeeeeeeeee"};
        Main.main(evilParams);
    }

    @Test
    public void testMain_givenWrongParametersNumber() throws Exception {
        String[] emptyArgs = {};
        Main.main(emptyArgs);

        String[] oneArg = {"a"};
        Main.main(oneArg);

        String[] threeArgs = {"a", "b", "c"};
        Main.main(threeArgs);
    }
}