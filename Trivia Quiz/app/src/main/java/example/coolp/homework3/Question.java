package example.coolp.homework3;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by coolp on 2/19/2018.
 */

public class Question implements Serializable{

        String quesNo;
        String question;
        String url;
        ArrayList<String> options= new ArrayList<>();
        String ans;
        public Question(){}

        public String getQuesNo() {
                return quesNo;
        }

        public void setQuesNo(String quesNo) {
                this.quesNo = quesNo;
        }

        public String getQuestion() {
                return question;
        }

        public void setQuestion(String question) {
                this.question = question;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public ArrayList<String> getOptions() {
                return options;
        }

        public void setOptions(ArrayList<String> options) {
                this.options = options;
        }

        public String getAns() {
                return ans;
        }

        public void setAns(String ans) {
                this.ans = ans;
        }

        @Override
        public String toString() {
            return quesNo + "\t" + question + "\t" + url +"\t" + ans +"\n";
        }
}


