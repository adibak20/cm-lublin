package example.rest.config;

public enum Endpoint {

    PATIENT_ADD("/patient"),
    PATIENT_DELETE("/patient/{params}"),
    PATIENT_FIND("/patient/findAllByKeywords/{params}"),
    PATIENT_LIST("/patient/list"),
    VISIT_LIST("/visit/getAllByPatient/{params}");

    private String patch;

    Endpoint(String patch) {
        this.patch = patch;
    }

    public String getPatch() {
        return patch;
    }

    public String getPatch(Long id) {
        return patch.replace("{params}", id.toString());
    }

    public String getPatch(String params) {
        return patch.replace("{params}", params);
    }
}
