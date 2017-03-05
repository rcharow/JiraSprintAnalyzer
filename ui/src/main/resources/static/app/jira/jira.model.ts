export class JiraBoard {
  public id: String;
  public self: String;
  public name: String;
  public type: String;

}

export class JiraSprint {
  public id: String;
  public self: String;
  public state: String;
  public name: String;
  public startDate: Date;
  public endDate: Date;
  public completeDate: Date;
  public originBoardId: String;
}


export class JiraSprintStats {
  public id: String;
  public name: String;
  public totalIssues: Number;
  public totalPoints: Number;
  public totalTimeSeconds: Number;
  public totalCost: Number;
}