import java.util.prefs.Preferences;

/**
 * Simple settings storage using java.util.prefs.Preferences.
 * Keeps application settings like clinic name, autosave, appointment duration and theme.
 */
public class SettingsDataStorage {
    private static final SettingsDataStorage INSTANCE = new SettingsDataStorage();
    private final Preferences prefs;

    // Keys
    private static final String KEY_CLINIC_NAME = "clinicName";
    private static final String KEY_AUTOSAVE = "autoSave";
    private static final String KEY_APPOINTMENT_MIN = "appointmentMinutes";

    // Defaults
    private static final String DEFAULT_CLINIC_NAME = "HealthCare Pro";
    private static final boolean DEFAULT_AUTOSAVE = true;
    private static final int DEFAULT_APPOINTMENT_MIN = 15;
    // single light theme - theming removed from settings storage

    private SettingsDataStorage() {
        prefs = Preferences.userNodeForPackage(SettingsDataStorage.class);
    }

    public static SettingsDataStorage getInstance() {
        return INSTANCE;
    }

    public String getClinicName() {
        return prefs.get(KEY_CLINIC_NAME, DEFAULT_CLINIC_NAME);
    }

    public void setClinicName(String name) {
        if (name == null) name = DEFAULT_CLINIC_NAME;
        prefs.put(KEY_CLINIC_NAME, name);
    }

    public boolean isAutoSaveEnabled() {
        return prefs.getBoolean(KEY_AUTOSAVE, DEFAULT_AUTOSAVE);
    }

    public void setAutoSaveEnabled(boolean enabled) {
        prefs.putBoolean(KEY_AUTOSAVE, enabled);
    }

    public int getAppointmentDurationMinutes() {
        return prefs.getInt(KEY_APPOINTMENT_MIN, DEFAULT_APPOINTMENT_MIN);
    }

    public void setAppointmentDurationMinutes(int minutes) {
        if (minutes <= 0) minutes = DEFAULT_APPOINTMENT_MIN;
        prefs.putInt(KEY_APPOINTMENT_MIN, minutes);
    }

    /**
     * Reset settings to defaults.
     */
    public void resetToDefaults() {
        setClinicName(DEFAULT_CLINIC_NAME);
        setAutoSaveEnabled(DEFAULT_AUTOSAVE);
        setAppointmentDurationMinutes(DEFAULT_APPOINTMENT_MIN);
    }
}
