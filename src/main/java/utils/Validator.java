package utils;

import entity.Result;

public class Validator {

    public boolean validate(Result result){
        return (validateX(result)) && (validateY(result)) && (validateR(result));
    }

    public boolean validateX(Result result){
        return (result.getX()!=null);
    }
    public boolean validateY(Result result){
        return (result.getY()>-3) && (result.getY()<3) && (result.getY()!=null);
    }

    public boolean validateR(Result result){
        return (result.getR()>2) && (result.getR()<5) && (result!=null);
    }

    public String checkX(Result result){
        if (result.getX()==null) return "You need to choose X!";
        return "";
    }

    public String checkY(Result result){
        if (result.getY()==null){
            return "You need to choose Y!";
        } else if ((result.getY()<=-3) || (result.getY()>=3)) {
            return "Y can take value in the range (-3 ... 3)!";
        } else return "";
    }

    public String checkR(Result result){
        if (result.getR()==null){
            return "You need to choose R!";
        } else if ( (result.getR()<=2) || (result.getR()>=5) ) {
            return "R can take value in the range (2 ... 5)!";
        } else {
            return "";
        }
    }
}
