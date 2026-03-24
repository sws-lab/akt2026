package week5.altgson.demo;

import com.google.gson.annotations.SerializedName;

public record Student(String firstname, String surname, int age) {


    // Siin on erinev variant annotatsioonidega :)
    public record StudentAnnotated (
            @SerializedName(value = "firstname", alternate = {"name", "nimi", "eesnimi"})
            String firstname,
            @SerializedName(value = "surname", alternate = {"familyname", "perekonnanimi", "perenimi"})
            String surname,
            @SerializedName(value = "age", alternate = "vanus")
            int age
    ) { }
}
