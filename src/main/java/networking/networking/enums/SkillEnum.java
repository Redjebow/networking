package networking.networking.enums;

import lombok.Getter;

@Getter
public enum SkillEnum {
    HTML(1, "HTML"),
    CSS(2, "CSS"),
    JavaScript(3, "JavaScript"),
    React(4, "React"),
    Angular(5, "Angular"),
    VueJS(6, "Vue.js"),
    Java(7, "Java"),
    Python(8, "Python"),
    NodeJS(9, "Node.js"),
    SQL(10, "SQL"),
    MongoDB(11, "MongoDB"),
    RESTfulAPIs(12, "RESTful APIs"),
    BusinessAnalysis(13, "Business Analysis"),
    ProjectManagement(14, "Project Management"),
    Communication(15, "Communication"),
    ProblemSolving(16, "Problem Solving"),
    TimeManagement(17, "Time Management");

    private final long id;
    private final String name;

    SkillEnum(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
