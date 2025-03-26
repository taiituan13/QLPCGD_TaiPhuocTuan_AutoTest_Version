package config;

import com.google.gson.reflect.TypeToken;

import models.AcademicDegree;
import models.Lecturer;
import models.Term;

import java.io.IOException;
import java.util.List;

public class ApiService {
    private final ApiClient apiClient;

    public ApiService() {
        this.apiClient = new ApiClient();
    }

    public List<AcademicDegree> getAcademicDegreeData() throws IOException, InterruptedException {
        return apiClient.callApi("/Phancong02/AcademicDegree/GetData", new TypeToken<List<AcademicDegree>>() {});
    }

    public List<Lecturer> getLecturerData() throws IOException, InterruptedException {
        return apiClient.callApi("/Phancong02/Lecturer/GetData", new TypeToken<List<Lecturer>>() {});
    }

    public List<Term> getTermData() throws IOException, InterruptedException {
        return apiClient.callApi("/Phancong02/Term/GetData", new TypeToken<List<Term>>() {});
    }
}
