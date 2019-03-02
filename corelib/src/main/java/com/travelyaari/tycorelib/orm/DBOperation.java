package com.travelyaari.tycorelib.orm;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Created by Triode on 5/10/2016.
 */
public final class DBOperation {


    /**
     * Holds the single ton instance
     */
    private static DBOperation mInstance;


    /**
     * getter function for mInstance
     *
     * @return the single ton instance of {@code DBOperation}
     */
    public static DBOperation acquire(){
        mInstance = (mInstance == null) ? new DBOperation() : mInstance;
        return mInstance;
    }


    /**
     * Constructs a new instance of {@code Object}.
     */
    private DBOperation() {
        super();
    }

    /**
     * function delete the list of values provided
     *
     * @param list {@code List} to be deleted
     * @param callBack the callback to notify the status
     */
    public void deleteAll(final List<CoreObject> list,
                          final ORMCallbacks.DeleteCallBack callBack){
        final Observable<Integer> observer = getDeleteRecordObservable(list);
        observer.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                callBack.onDelete(null);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onDelete(new Exceptions.DeleteException(e));
            }

            @Override
            public void onNext(Integer integer) {

            }
        });
    }

    /**
     * function save the list of values provided
     *
     * @param list {@code List} to be saved
     * @param callBack the callback to notify the status
     */
    public void saveAll(final List<CoreObject> list,
                        final ORMCallbacks.SaveAllCallBack callBack){
        final Observable<List<CoreObject>> observer = getSaveRecordObservable(list);
        observer.subscribe(new Subscriber<List<CoreObject>>() {
            List<CoreObject> records;
            @Override
            public void onCompleted() {
                callBack.onSave(records);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onSave(null);
            }

            @Override
            public void onNext(List<CoreObject> records) {
                this.records = records;
            }
        });
    }








    /**
     * function create the Observable and returns it
     *
     * @param list values to be deleted
     * @return number {@code Observable}
     */
    private Observable<Integer> getDeleteRecordObservable(final List<CoreObject> list){
        return Observable.create(
                new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> sub) {
                        try{
                            final int count = CoreObject.delete(getIds(list), list.get(0).getmClassName());
                            sub.onNext(count);
                            sub.onCompleted();
                        }catch (Exceptions.DeleteException e){
                            sub.onError(e.getCause());
                        }
                    }
                }
        ).subscribeOn(Schedulers.computation());
    }


    /**
     * function returns all the id belongs to the object
     *
     * @param list the list against which the ids dto be obtained
     * @return array of ids
     * @throws {@code com.travelyaari.tycorelib.orm.Exceptions.DeleteException}
     */
    private String[] getIds(final List<CoreObject> list) throws Exceptions.DeleteException{
        if(list != null){
            String className = null;
            String[] ids = new String[list.size()];
            int index = 0;
            for (CoreObject object : list){
                if(className == null){
                    className = object.getmClassName();
                }else if(!className.equals(object.getmClassName())){
                    throw new Exceptions.DeleteException("The list should contains same object types");
                }
                ids[index] = object.mObjectId;
                index++;
            }
            return ids;
        }else{
            throw new Exceptions.DeleteException("The list cannot be null");
        }
    }

    /**
     * function returns the observable lto save all the items
     *
     * @param list which needs to be saved
     *
     * @return the created Observer
     */
    private Observable<List<CoreObject>> getSaveRecordObservable(final List<CoreObject> list){
        return Observable.create(
                new Observable.OnSubscribe<List<CoreObject>>() {
                    @Override
                    public void call(Subscriber<? super List<CoreObject>> sub) {
                        final List<CoreObject> results = CoreObject.saveAll(list);
                        sub.onNext(results);
                        sub.onCompleted();
                    }
                }
        ).subscribeOn(Schedulers.computation());
    }

}
