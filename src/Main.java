import java.util.*;

class survey {
    String tittle;
    String topic;
    String description;
    List<user> users = new ArrayList<>();
    List<question> questions= new ArrayList<>();

    public survey(String tittle, String topic, String description) {
        this.tittle = tittle;
        this.topic = topic;
        this.description = description;
    }

    //Find the most answered question of the interview
    public question topQuestion() {
        return questions.stream().max(Comparator.comparing(q -> q.answer.values()
                        .stream().mapToInt(Integer::intValue).sum()))
                .orElse(null);
    }


    //Print survey results
    public void printResults(){
        for(question q : questions){
            System.out.println(q.ask);
            q.answer.forEach((answer,c)->{
                System.out.println(answer+" "+c);
            });
        }
    }

    //Find answers given by a candidate
    public Map<question, String> userAnswers(user u){
        return u.answers;
    }

    //Find candidate with the most survays
    public user mostSurvey(){
        return users.stream().max(Comparator.comparingInt(u -> u.answers.size())).orElse(null);
    }

    //Adding a question
    public void addQuestion(question q) {
        questions.add(q);
    }

    //Remove question
    public void removeQuestion(question q) {
        questions.remove(q);
    }

    //Remove question if answert less than 50% of users
    public void removeUnansweredQuestions() {
        int allUsers = users.size();
        questions.removeIf(q -> q.answer.values().stream().mapToInt(Integer::intValue).sum() < allUsers / 2);
    }

    // Validating Survey
    public boolean validate() {
        if (questions.size() < 10 && questions.size() > 40 &&
                questions.stream().map(q -> q.ask).distinct().count() == questions.size()){
            return true;
        }else return false;
    }
}

class user {
    String firstName;
    String lastName;
    String email;
    String phone;
    Map<question, String> answers = new HashMap<>();

    public user(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}

class question  {
    String ask;
    static final String agree = "Agree";
    static final String slightlyAgree = "Slightly Agree";
    static final String slightlyDisgree = "Slightly Disagree";
    static final String disagree = "Disagree";
    Map<String, Integer> answer = new HashMap<>();

    public question(int agree, int slightlyAgree, int disagree, int slightlyDisgree) {

        this.ask = ask;
        answer.put("agree", 0);
        answer.put("slightlyAgree", 0);
        answer.put("slightlyDisagree", 0);
        answer.put("disagree", 0);

    }

}



public class Main {
    public static void main(String[] args) {

        survey testSurvey = new survey("Test", "Technology", "Testing");


        user user1 = new user("alan", "soe", "alansoe@gmail.com", "0648839453");
        testSurvey.users.add(user1);


        question question1 = new question(0,0,0,0);
        testSurvey.addQuestion(question1);


        System.out.println("Survey Results:");
        testSurvey.printResults();


        question topQuestion = testSurvey.topQuestion();
        System.out.println("\nThe most answered question:");
        System.out.println(topQuestion.ask);


        System.out.println("\n" + user1.firstName + " " + user1.lastName + "'s answers:");
        Map<question, String> userAnswers = testSurvey.userAnswers(user1);
        userAnswers.forEach((q, answer) -> System.out.println(q.ask + ": " + answer));


        user mostSurveyed = testSurvey.mostSurvey();
        System.out.println("\nUser who answered the most questions:");
        System.out.println(mostSurveyed.firstName + " " + mostSurveyed.lastName);


        System.out.println("\nRemoving unanswered questions...");
        testSurvey.removeUnansweredQuestions();
        System.out.println("Remaining questions after removal:");
        testSurvey.questions.forEach(q -> System.out.println(q.ask));


        boolean isValid = testSurvey.validate();
        System.out.println("\nIs the survey valid? " + isValid);

    }
}
