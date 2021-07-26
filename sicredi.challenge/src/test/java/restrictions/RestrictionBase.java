package restrictions;

import base.BaseAPI;
import factory.CpfDataFactory;

public abstract class RestrictionBase extends BaseAPI {

    public static final String BASE_PATH_RESTRICTION = "restricoes/{cpf}";
    protected final CpfDataFactory cpfDataFactory;

    public RestrictionBase() {
        cpfDataFactory = new CpfDataFactory();
    }

}
