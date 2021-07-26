package factory;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CpfDataFactory {

    private final Faker faker;

    public CpfDataFactory() {
        this.faker = new Faker();
    }

    public String getRandomCpf() {
        return String.valueOf(faker.number().randomNumber(11, false));
    }

    public String getCpfWithRestricition() {

        List<String> cpfWithRestriction =
                Arrays.asList(
                        "97093236014",
                        "60094146012",
                        "84809766080",
                        "62648716050",
                        "26276298085",
                        "01317496094",
                        "55856777050",
                        "19626829001",
                        "24094592008",
                        "58063164083"
                );

        String randomCpf = cpfWithRestriction.get(new Random().nextInt(cpfWithRestriction.size()));

        return randomCpf;
    }

}
