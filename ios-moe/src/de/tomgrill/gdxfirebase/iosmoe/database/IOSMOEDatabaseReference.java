package de.tomgrill.gdxfirebase.iosmoe.database;

import apple.foundation.*;
import com.google.firebasedatabase.FIRDatabase;
import com.google.firebasedatabase.FIRDatabaseReference;
import com.google.firebasedatabase.FIRServerValue;
import de.tomgrill.gdxfirebase.core.database.DatabaseReference;
import de.tomgrill.gdxfirebase.core.database.FirebaseDatabase;
import de.tomgrill.gdxfirebase.core.database.ServerValue;

import java.util.List;
import java.util.Map;

public class IOSMOEDatabaseReference extends IOSMOEQuery implements DatabaseReference {

    private FIRDatabaseReference firDatabaseReference;
    private FIRDatabase firDatabase;

    public IOSMOEDatabaseReference(FIRDatabaseReference firDatabaseReference, FIRDatabase firDatabase) {
        super(firDatabaseReference);
        this.firDatabaseReference = firDatabaseReference;
        this.firDatabase = firDatabase;
    }

    @Override
    public DatabaseReference child(String s) {
        return new IOSMOEDatabaseReference(firDatabaseReference.child(s), firDatabase);
    }

    @Override
    public DatabaseReference push() {
        return new IOSMOEDatabaseReference(firDatabaseReference.childByAutoId(), firDatabase);
    }

    @Override
    public void setValue(Object value) {
        firDatabaseReference.setValue(value);
    }

    @Override
    public void setValue(Object value, Object priority) {
        firDatabaseReference.setValueAndPriority(value, priority);
    }

    @Override
    public void setValue(Object value, final CompletionListener listener) {
        firDatabaseReference.setValueWithCompletionBlock(value, new FIRDatabaseReference.Block_setValueWithCompletionBlock() {
            @Override
            public void call_setValueWithCompletionBlock(NSError error, FIRDatabaseReference databaseReference) {
                if (error == null) {
                    listener.onComplete(null, new IOSMOEDatabaseReference(databaseReference, firDatabase));
                } else {
                    listener.onComplete(new IOSMOEDatabaseError(error), new IOSMOEDatabaseReference(databaseReference, firDatabase));
                }
            }
        });
    }

    @Override
    public void setValue(Object value, Object priority, final CompletionListener listener) {
        firDatabaseReference.setValueAndPriorityWithCompletionBlock(value, priority, new FIRDatabaseReference.Block_setValueAndPriorityWithCompletionBlock() {
            @Override
            public void call_setValueAndPriorityWithCompletionBlock(NSError error, FIRDatabaseReference databaseReference) {
                if (error == null) {
                    listener.onComplete(null, new IOSMOEDatabaseReference(databaseReference, firDatabase));
                } else {
                    listener.onComplete(new IOSMOEDatabaseError(error), new IOSMOEDatabaseReference(databaseReference, firDatabase));
                }
            }
        });
    }

    @Override
    public void setPriority(Object priority) {
        firDatabaseReference.setPriority(priority);
    }

    @Override
    public void setPriority(Object priority, final CompletionListener listener) {
        firDatabaseReference.setPriorityWithCompletionBlock(priority, new FIRDatabaseReference.Block_setPriorityWithCompletionBlock() {
            @Override
            public void call_setPriorityWithCompletionBlock(NSError error, FIRDatabaseReference databaseReference) {
                if (error == null) {
                    listener.onComplete(null, new IOSMOEDatabaseReference(databaseReference, firDatabase));
                } else {
                    listener.onComplete(new IOSMOEDatabaseError(error), new IOSMOEDatabaseReference(databaseReference, firDatabase));
                }
            }
        });
    }

    @Override
    public void updateChildren(Map<String, Object> update) {
        NSDictionary<NSString, Object> dictionary = toDictionary(update);

        System.out.println("IN DIC 1 ");
        for (Map.Entry<NSString, Object> entry : dictionary.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        firDatabaseReference.updateChildValues(dictionary);
    }


    @Override
    public void updateChildren(Map<String, Object> update, CompletionListener listener) {
        NSDictionary<NSString, Object> dictionary = toDictionary(update);

        System.out.println("IN DIC 2");
        for (Map.Entry<NSString, Object> entry : dictionary.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


        firDatabaseReference.updateChildValuesWithCompletionBlock(dictionary, new FIRDatabaseReference.Block_updateChildValuesWithCompletionBlock() {
            @Override
            public void call_updateChildValuesWithCompletionBlock(NSError error, FIRDatabaseReference databaseReference) {
                if (error == null) {
                    listener.onComplete(null, new IOSMOEDatabaseReference(databaseReference, firDatabase));
                } else {
                    listener.onComplete(new IOSMOEDatabaseError(error), new IOSMOEDatabaseReference(databaseReference, firDatabase));
                }
            }
        });
    }

    private NSDictionary<NSString, Object> toDictionary(Map<String, Object> map) {
        NSMutableDictionary<NSString, Object> dictionary = (NSMutableDictionary<NSString, Object>) NSMutableDictionary.dictionary();

        for (Map.Entry<String, Object> entry : map.entrySet()) {

            if(entry.getValue() == ServerValue.TIMESTAMP) {
                dictionary.put(NSString.stringWithString(entry.getKey()), FIRServerValue.timestamp());
                continue;
            }

            if (entry.getValue() instanceof Map) {
                NSDictionary<NSString, Object> dictionaryValue = toDictionary((Map) entry.getValue());
                dictionary.put(NSString.stringWithString(entry.getKey()), dictionaryValue);
                continue;
            }

            if (entry.getValue() instanceof List) {
                throw new UnsupportedOperationException("List is not yet supported");
            }
            if (entry.getValue() instanceof Integer) {
                dictionary.put(NSString.stringWithString(entry.getKey()), NSNumber.numberWithInt((Integer) entry.getValue()));
                continue;
            }

            if (entry.getValue() instanceof Long) {
                dictionary.put(NSString.stringWithString(entry.getKey()), NSNumber.numberWithLong((Long) entry.getValue()));
                continue;
            }

            if (entry.getValue() instanceof String) {
                dictionary.put(NSString.stringWithString(entry.getKey()), NSString.stringWithString((String) entry.getValue()));
                continue;
            }

            if (entry.getValue() instanceof Boolean) {
                if ((Boolean) entry.getValue()) {
                    dictionary.put(NSString.stringWithString(entry.getKey()), NSNumber.numberWithInt(1));
                } else {
                    dictionary.put(NSString.stringWithString(entry.getKey()), NSNumber.numberWithInt(0));
                }
                continue;
            }

        }


        return dictionary;
    }

    @Override
    public void removeValue() {
        firDatabaseReference.removeValue();
    }

    @Override
    public void removeValue(CompletionListener listener) {
        firDatabaseReference.removeValueWithCompletionBlock(new FIRDatabaseReference.Block_removeValueWithCompletionBlock() {
            @Override
            public void call_removeValueWithCompletionBlock(NSError error, FIRDatabaseReference databaseReference) {
                if (error == null) {
                    listener.onComplete(null, new IOSMOEDatabaseReference(databaseReference, firDatabase));
                } else {
                    listener.onComplete(new IOSMOEDatabaseError(error), new IOSMOEDatabaseReference(databaseReference, firDatabase));
                }
            }
        });
    }

    @Override
    public FirebaseDatabase getDatabase() {
        return new IOSMOEFirebaseDatabase();
    }

    @Override
    public DatabaseReference getParent() {
        return new IOSMOEDatabaseReference(firDatabaseReference.parent(), firDatabase);
    }

    @Override
    public DatabaseReference getRoot() {
        return new IOSMOEDatabaseReference(firDatabaseReference.root(), firDatabase);
    }

    @Override
    public String getKey() {
        return firDatabaseReference.key();
    }
}
