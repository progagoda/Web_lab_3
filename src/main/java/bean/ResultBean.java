package bean;

import entity.Result;
import utils.DataBaseManager;
import utils.HitChecker;
import utils.Validator;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@SessionScoped
public class ResultBean {
    private String xError = "";
    private String yError = "";
    private String rError = "";
    private int pointer5 = 0;
    private int pointer2 = 0;
    private int isPointer = 0;
    private Result newResult = new Result();
    private Result clickResult = new Result();
    private Validator validator = new Validator();
    private List<Result> resultList = new ArrayList<>();
    private int isDrown;
    private DataBaseManager dataBaseManager = new DataBaseManager();

    public void addClick() {
        System.out.println(clickResult);
        clickResult.setR(newResult.getR());

        makeClickErrors();
        System.out.println(clickResult);
        if (validator.checkR(clickResult).equals("")) {
            makeResult(clickResult);
            resultList.add(clickResult);
            DataBaseManager.addBean(clickResult);
            clickResult = new Result();
            saveSubmitValues(newResult.getX(), newResult.getY(), newResult.getR());
        }
    }

    public int getIsDrown() {
        return isDrown;
    }

    public void setIsDrown(int isDrown) {
        this.isDrown = isDrown;
    }

    public void addResult() {
        makeSubmitErrors();
        if ((newResult.getX() != null) && (newResult.getY() != null) && (newResult.getR() != null)) {
            if (validator.validate(newResult)) {
                makeResult(newResult);
                resultList.add(newResult);
                DataBaseManager.addBean(newResult);

                saveSubmitValues(newResult.getX(), newResult.getY(), newResult.getR());
                System.out.println("addResultMethod");
            } else {
                newResult.setR(4.0f);
            }
        }
    }

    public void update() {
        DataBaseManager.load(resultList);
    }

    public void makeClickErrors() {
        xError = "";
        yError = "";
        rError = validator.checkR(clickResult);
    }


    public void makeSubmitErrors() {
        xError = validator.checkX(newResult);
        yError = validator.checkY(newResult);
        rError = validator.checkR(newResult);
    }


    public void addCheck() {
        isPointer = 1;
        if (validator.validateR(newResult)) {
            isDrown = 1;
            if (clickResult.getX() != null && clickResult.getY() != null) addClick();
            else addResult();
        } else {
            rError = validator.checkR(newResult);
            newResult.setR(4.0f);
        }
        System.out.println("x = " + newResult.getX());
        System.out.println("y = " + newResult.getY());
        System.out.println("r = " + newResult.getR());
    }


    public void saveSubmitValues(Float x, Float y, Float r) {
        newResult = new Result();
        newResult.setX(x);
        newResult.setY(y);
        newResult.setR(r);
    }

    public void makeResult(Result result) {
        result.setCurrentTime(new TimeBean().getTime());
        long start = System.nanoTime();
        result.setResult(new HitChecker().isHit(result.getX(), result.getY(), result.getR()));
        result.setExecutionTime((System.nanoTime() - start) / 1000);
        System.out.println("make result!");
    }

    public void defaultResult() {
        newResult.setX(null);
        newResult.setY(null);
        newResult.setR(null);
        clickResult.setX(null);
        clickResult.setY(null);
        clickResult.setR(null);
        isPointer = 0;
        xError = "";
        yError = "";
        rError = "";
        isDrown = 0;
        isPointer = 0;
    }


    public String getxError() {
        return xError;
    }

    public void setxError(String xError) {
        this.xError = xError;
    }

    public String getyError() {
        return yError;
    }

    public void setyError(String yError) {
        this.yError = yError;
    }

    public String getrError() {
        return rError;
    }

    public void setrError(String rError) {
        this.rError = rError;
    }

    public int getPointer5() {
        return pointer5;
    }

    public void setPointer5(int pointer5) {
        this.pointer5 = pointer5;
    }

    public int getPointer2() {
        return pointer2;
    }

    public void setPointer2(int pointer2) {
        this.pointer2 = pointer2;
    }

    public int getIsPointer() {
        return isPointer;
    }

    public void setIsPointer(int isPointer) {
        this.isPointer = isPointer;
    }

    public Result getNewResult() {
        return newResult;
    }

    public void setNewResult(Result newResult) {
        this.newResult = newResult;
    }

    public Result getClickResult() {
        return clickResult;
    }

    public void setClickResult(Result clickResult) {
        this.clickResult = clickResult;
    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public List<Result> getResultList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String id = facesContext.getExternalContext().getSessionId(false);
        List<Result> resId = new ArrayList<>();
        DataBaseManager.load(resultList);
        if (resultList.size() > 0) {
            for (Result val : resultList) {
                if ((val.getSession_id().equals(id))) {
                    resId.add(val);
                }
            }
            return resId;
        }
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

}
