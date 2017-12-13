
import com.course.ums.ws.*;
import com.course.ums.ws.course.AddCourse;
import com.course.ums.ws.course.ListCourses;
import com.course.ums.ws.exam.AddExam;
import com.course.ums.ws.exam.ListExams;
import com.course.ums.ws.exam.ListViewExams;
import com.course.ums.ws.group.AddGroup;
import com.course.ums.ws.group.ListGroups;
import com.course.ums.ws.groupStudent.AddGroupStudent;
import com.course.ums.ws.groupStudent.RemoveGroupStudent;
import com.course.ums.ws.groupTeacher.AddGroupTeacher;
import com.course.ums.ws.groupTeacher.RemoveGroupTeacher;
import com.course.ums.ws.semester.AddSemester;
import com.course.ums.ws.semester.ListSemesters;
import com.course.ums.ws.teacherCourse.AddTeacherCourse;
import com.course.ums.ws.teacherCourse.RemoveTeacherCourse;
import com.course.ums.ws.user.*;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by vh on 11/2/17.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        Spark.port(8080);
        Spark.post("/hello", new Route() {
            @Override
            public Object handle(Request request, Response response) {
                return "world";
            }
        });
        Spark.post("/user/add", new AddUser());
        Spark.post("/user/list", new ListUsers());
        Spark.get("/user/list", new ListUsers());

        //adding
        Spark.post("user/authenticate", new Authenticate());
        Spark.post("user/student/add", new AddStudent());
        Spark.post("user/teacher/add", new AddTeacher());
        Spark.post("/course/add", new AddCourse());
        Spark.post("/semester/add", new AddSemester());
        Spark.post("/group/add", new AddGroup());
        Spark.post("/teacher/course/add", new AddTeacherCourse());
        Spark.post("/group/teacher/add", new AddGroupTeacher());
        Spark.post("/group/student/add", new AddGroupStudent());
        Spark.post("/group/teacher/add", new AddGroupTeacher());

        Spark.post("/exam/add", new AddExam());

        //listing
        Spark.post("user/student/list", new ListStudents());
        Spark.post("user/teacher/list", new ListTeachers());
        Spark.post("course/list", new ListCourses());
        Spark.post("semester/list", new ListSemesters());
        Spark.post("group/list", new ListGroups());
        Spark.post("/exam/list", new ListExams());
        Spark.post("exam/list_view", new ListViewExams());

        //remove
        Spark.post("/teacher/course/remove", new RemoveTeacherCourse());
        Spark.post("/group/teacher/remove", new RemoveGroupTeacher());
        Spark.post("/group/student/remove", new RemoveGroupStudent());

        //this is just for test
        JSONObject test = new JSONObject();
        test.put("id", 0);
        test.put("text", "hello world");

        System.out.println(test);

        test = new JSONObject("{hello: world, bla : blabla}");
        System.out.println(test.get("hello"));
        System.out.println(test.get("bla"));
    }
}