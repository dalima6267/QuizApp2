package com.dalima.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_question.opt_1
import kotlinx.android.synthetic.main.activity_question.opt_2
import kotlinx.android.synthetic.main.activity_question.opt_3
import kotlinx.android.synthetic.main.activity_question.opt_4
import kotlinx.android.synthetic.main.activity_question.progress_bar
import kotlinx.android.synthetic.main.activity_question.progress_text
import kotlinx.android.synthetic.main.activity_question.question_text
import kotlinx.android.synthetic.main.activity_question.submit

class QuestionActivity : AppCompatActivity() {
    private var Name:String?=null
    private var score:Int=0
// 9 for selected option
    private var currentPosition:Int=1
    //8 current position of  question
    private var questionList:ArrayList<QuestionData> ? = null
    //1  all the questions stored  in the arraylist
    private var selecedOption:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        Name=intent.getStringExtra(setData.name)
//The value of th ename that was on the setdata has been taken into the name(string)
        questionList=setData.getQuestion()
        //2 All the questions are taken from thE setdata and stored in the question list

        setQuestion()
// To dull the color of options
        opt_1.setOnClickListener{
//  if selected option is 1 then called selectedoptionstyle
            selectedOptionStyle(opt_1,1)
        }
        opt_2.setOnClickListener{

            selectedOptionStyle(opt_2,2)
        }
        opt_3.setOnClickListener{

            selectedOptionStyle(opt_3,3)
        }
        opt_4.setOnClickListener{

            selectedOptionStyle(opt_4,4)
        }

        submit.setOnClickListener {
            //
            if(selecedOption!=0)
            {
                // if selected option are not 0
                val question=questionList!![currentPosition-1]
                if(selecedOption!=question.correct_ans)
                {
                    // if selected option are not correct the color become red
                    setColor(selecedOption,R.drawable.wrong_question_option)
                }else{
                    score++;
                    // if answer is not wrong then increases the score
                }
                setColor(question.correct_ans,R.drawable.correct_question_option)
                if(currentPosition==questionList!!.size)
                    // this is last question
                    submit.text="FINISH"
                else
                    // not last question
                    submit.text="Go to Next"
            }else{
                // if user not selected any option then current position of question is increases
                currentPosition++
                when{
                    currentPosition<=questionList!!.size->{
                        //set new question position
                        setQuestion()
                    }
                    else->{
                        // if all questions are completed of user then user can go to next activity
                        var intent= Intent(this,Result::class.java)
                        intent.putExtra(setData.name,Name.toString())
                        intent.putExtra(setData.score,score.toString())
                        // The value of score and name is sent to the next activity
                        intent.putExtra("total size",questionList!!.size.toString())

                        startActivity(intent)
                        finish()
                    }
                }
            }
            selecedOption=0
            // for the next question set selectedoption 0
        }

    }

    fun setColor(opt:Int,color:Int){
        when(opt){
            // if option are correct the color become green
            1->{
                opt_1.background=ContextCompat.getDrawable(this,color)
            }
            2->{
                opt_2.background=ContextCompat.getDrawable(this,color)
            }
            3->{
                opt_3.background=ContextCompat.getDrawable(this,color)
            }
            4->{
                opt_4.background=ContextCompat.getDrawable(this,color)
            }
        }
    }


    fun setQuestion(){
//
        val question = questionList!![currentPosition-1]
        //3 index 0 will store th question which is in setdata
        setOptionStyle()


        progress_bar.progress=currentPosition
        //6 current position of the progressbar
        progress_bar.max=questionList!!.size
        progress_text.text="${currentPosition}"+"/"+"${questionList!!.size}"
        // currentposition+max
        question_text.text=question.question
        //4 shows any one question from all questions
        opt_1.text=question.option_one
        //5 These are all the options for that one question
        opt_2.text=question.option_tw0
        opt_3.text=question.option_three
        opt_4.text=question.option_four

    }

    fun setOptionStyle(){
        //


        var optionList:ArrayList<TextView> = arrayListOf()
        // create a arraylist type of textview
        optionList.add(0,opt_1)
        // add all the option according to the index in the arraylist
        optionList.add(1,opt_2)
        optionList.add(2,opt_3)
        optionList.add(3,opt_4)

        for(op in optionList)
        // take option one by one
        {
            op.setTextColor(Color.parseColor("#555151"))
            // set color of  options
            op.background= ContextCompat.getDrawable(this,R.drawable.question_option)
            // set background of  options
            op.typeface= Typeface.DEFAULT
            // bold
        }
    }

    fun selectedOptionStyle(view:TextView,opt:Int){
//
        setOptionStyle()
        // remaining options are default color
        selecedOption=opt
        // assign value of selected option

        view.background=ContextCompat.getDrawable(this,R.drawable.selected_question_option)
        // border of purple color of selected option
        view.typeface= Typeface.DEFAULT_BOLD
        // bold
        view.setTextColor(Color.parseColor("#000000"))

    }
}