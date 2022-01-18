package com.example.yourmovee

import android.content.res.Resources
import android.icu.number.NumberFormatter.with
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    var BASE_URL = "https://api.themoviedb.org"
    var API_KEY = "5ce1b056c9f053716807d9ffe0151702"
    var movieIdList = ArrayList<MovieId>()
    var movies = ArrayList<String>()
    var i = 0
    var flag = 0
    var context = this
    private var recommendation = ArrayList<Recommendation>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readMovieId()
        readRecommendation()

        var myAdapter = ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,movies)
        editText.setAdapter(myAdapter)

        btn.setOnClickListener {
            popularMovies.text = "Recommended Movies"
            for (etMovie in movieIdList){
                if (etMovie.name == editText.text.toString()) {
                    flag = 1
                    break
                }
                i++
            }

            if (editText.text.toString().isEmpty())
                Toast.makeText(context,"Please enter a movie",Toast.LENGTH_SHORT).show()
            else if (flag == 0)
                Toast.makeText(context,"Sorry! Movie not found,please enter a movie from suggestions",Toast.LENGTH_LONG)
                    .show()
            else{
                var retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                var myInterface:ApiInterface = retrofit.create(ApiInterface::class.java)


                var call: Call<MovieResults> =
                    myInterface.listOfMovies(recommendation[i].movies[0], API_KEY)
                call.enqueue(object :Callback<MovieResults>{
                    override fun onResponse(
                        call: Call<MovieResults>,
                        response: Response<MovieResults>
                    ) {
                        var movieResults:MovieResults = response.body()!!
                        movieResults.poster_path =
                            "https://image.tmdb.org/t/p/w500/${movieResults.poster_path}"
                        movie1.text = movieResults.title
                        Glide.with(context).load(movieResults.poster_path).into(image1)
                        rating1.text = movieResults.vote_average+"/10"
                    }
                    override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    }

                })
                call = myInterface.listOfMovies(recommendation[i].movies[1], API_KEY)
                call.enqueue(object :Callback<MovieResults>{
                    override fun onResponse(
                        call: Call<MovieResults>,
                        response: Response<MovieResults>
                    ) {
                        var movieResults:MovieResults = response.body()!!
                        movieResults.poster_path =
                            "https://image.tmdb.org/t/p/w500/${movieResults.poster_path}"
                        movie2.text = movieResults.title
                        Glide.with(context).load(movieResults.poster_path).into(image2)
                        rating2.text = movieResults.vote_average+"/10"
                    }
                    override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    }

                })
                call = myInterface.listOfMovies(recommendation[i].movies[2], API_KEY)
                call.enqueue(object :Callback<MovieResults>{
                    override fun onResponse(
                        call: Call<MovieResults>,
                        response: Response<MovieResults>
                    ) {
                        var movieResults: MovieResults = response.body()!!
                        movieResults.poster_path =
                            "https://image.tmdb.org/t/p/w500/${movieResults.poster_path}"
                        movie3.text = movieResults.title
                        Glide.with(context).load(movieResults.poster_path).into(image3)
                        rating3.text = movieResults.vote_average+"/10"
                    }

                    override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    }

                })
                call = myInterface.listOfMovies(recommendation[i].movies[3], API_KEY)
                call.enqueue(object :Callback<MovieResults>{
                    override fun onResponse(
                        call: Call<MovieResults>,
                        response: Response<MovieResults>
                    ) {
                        var movieResults:MovieResults = response.body()!!
                        movieResults.poster_path =
                            "https://image.tmdb.org/t/p/w500/${movieResults.poster_path}"
                        movie4.text = movieResults.title
                        Glide.with(context).load(movieResults.poster_path).into(image4)
                        rating4.text = movieResults.vote_average+"/10"
                    }
                    override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    }

                })
                call = myInterface.listOfMovies(recommendation[i].movies[4], API_KEY)
                call.enqueue(object :Callback<MovieResults>{
                    override fun onResponse(
                        call: Call<MovieResults>,
                        response: Response<MovieResults>
                    ) {
                        var movieResults:MovieResults = response.body()!!
                        movieResults.poster_path =
                            "https://image.tmdb.org/t/p/w500/${movieResults.poster_path}"
                        movie5.text = movieResults.title
                        Glide.with(context).load(movieResults.poster_path).into(image5)
                        rating5.text = movieResults.vote_average+"/10"
                    }
                    override fun onFailure(call: Call<MovieResults>, t: Throwable) {
                    }

                })
            }

            i = 0
            flag = 0

        }

    }

    private fun readMovieId() {
        var stream:InputStream = resources.openRawResource(R.raw.movie_id)
        var reader:BufferedReader = BufferedReader(
            InputStreamReader(stream,Charset.forName("UTF-8"))
            )
        var line:String? = ""
        try{
            var tokens: ArrayList<String>
            while ((reader.readLine().also { line = it }) != null){
                var movieId:MovieId = MovieId()
                tokens = line?.split(",") as ArrayList<String>
                movieId.id = tokens[0]
                movieId.name = tokens[1]
                movieIdList.add(movieId)
                movies.add(tokens[1])
            }
        }catch (e: IOException){

        }
    }
    private fun readRecommendation() {
        var stream:InputStream = resources.openRawResource(R.raw.recommendation2)
        var reader:BufferedReader = BufferedReader(
            InputStreamReader(stream,Charset.forName("UTF-8"))
        )
        var line:String? = ""
        try{
            var tokens: ArrayList<String>
            while ((reader.readLine().also { line = it }) != null){
                var recommend = Recommendation()
                tokens = line?.split(",") as ArrayList<String>
                recommend.movies.add(tokens[0])
                recommend.movies.add(tokens[1])
                recommend.movies.add(tokens[2])
                recommend.movies.add(tokens[3])
                recommend.movies.add(tokens[4])
                recommendation.add(recommend)
            }
        }catch (e: IOException){

        }
    }
}