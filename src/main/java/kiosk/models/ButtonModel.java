package kiosk.models;

public final class ButtonModel {

    public String text;
    public String target;
    public boolean isCircle;
    public int[] rgb;
    // Optional. Null is a valid value
    public ImageModel image;

    /**
     * Create a new button model with default values.
     * They will need to overwritten
     */
    public ButtonModel() {
        this.text = "null";
        this.target = "null";
        this.isCircle = false;
        this.rgb = new int[] { 112, 191, 76 };
        this.image = null;
    }

    /**
     * Constructs a button model.
     * @param text to display inside the button
     * @param target the scene this button links to
     */
    public ButtonModel(String text, String target) {
        this.text = text;
        this.target = target;
    }

    /**
     * Create a deep copy of the button model. There should
     * be zero shared references between the copy and the original.
     * It can modified as pleased.
     * @return copy of the current button model
     */
    public ButtonModel deepCopy() {
        ButtonModel newButton = new ButtonModel();
        newButton.target = this.target;
        newButton.text = this.text;
        newButton.isCircle = this.isCircle;
        newButton.rgb = this.rgb.clone();
        newButton.image = this.image == null ? null : this.image.deepCopy();
        return newButton;
    }
}
