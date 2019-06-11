package com.example.fakenews.apiWeb;

import com.example.fakenews.apiModel.LoginBody;
import com.example.fakenews.apiModel.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    /*@GET("/albums/{id}")
    public Call<Album> getAlbumWithID(@Path("id") int id);

    @GET("/albums/")
    public Call<List<Album>> getAllAlbums();

    @POST("/albums")
    public Call<Album> albumData(@Body Album data);*/

    /*@GET("/admin/usuario")
    public Call<List<DtUsuario>> getAllUsuarios();*/

    /*@GET("director/carrera/")
    public Call<List<DtCarrera>> getAllCarreras();*/

    //@Headers({"Content-type: application/json",
            //"Accept: */*"})
    @POST("login/")  // call<String>
    Call<LoginResponse> login(@Body LoginBody loginBody);

/*
    @POST("estudiante/token/")
    Call<String> enviarTokenFirebase(@Header("Authorization") String authorization, @Header("Content-Type") String contentType, @Body TokenFirebaseBody tokenFirebaseBody);

    @GET("director/carrera/")
    public Call<List<DtCarrera>> getAllCarreras(@Header("Authorization") String authorization);

    @GET("estudiante/curso/{cedula}/")
    public Call<List<DtCurso>> getCursosByCedula(@Header("Authorization") String authorization, @Path("cedula") String cedula);

    @GET("estudiante/examen/{cedula}/")
    public Call<List<DtExamen>> getExamenesByCedula(@Header("Authorization") String authorization, @Path("cedula") String cedula);

    @GET("estudiante/consultarCalificaciones/{cedula}/")
    public Call<DtCalificaciones> getCalificacionesSAsig(@Header("Authorization") String authorization,
                                                         @Path("cedula") String cedula);

    @GET("estudiante/consultarCalificaciones/{cedula}/{idAsig_Carrera}/")
    public Call<DtCalificaciones> getCalificaciones(@Header("Authorization") String authorization,
                                                    @Path("cedula") String cedula,
                                                    @Path("idAsig_Carrera") Long idAsig_Carrera);

    @POST("estudiante/inscripcionCarrera/")
    public Call<String> inscripcionCarrera(@Header("Authorization") String authorization, @Header("Content-Type") String contentType, @Body InscripcionCarreraBody inscripcionCarreraBody);

    @POST("estudiante/inscripcionCurso/")
    public Call<String> inscripcionCurso(@Header("Authorization") String authorization, @Header("Content-Type") String contentType, @Body InscripcionCursoBody inscripcionCursoBody);

    @POST("estudiante/inscripcionExamen/")
    public Call<String> inscripcionExamen(@Header("Authorization") String authorization, @Header("Content-Type") String contentType, @Body InscripcionExamenBody inscripcionExamenBody);
*/
}
