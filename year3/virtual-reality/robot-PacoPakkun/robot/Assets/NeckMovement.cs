using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Serialization;

public class NeckMovement : MonoBehaviour
{
    public Vector2 headPosition;
    public Vector2 mousePosition;

    void Update()
    {
        headPosition = Camera.main.WorldToViewportPoint(transform.position);
        mousePosition = (Vector2) Camera.main.ScreenToViewportPoint(Input.mousePosition);
        float angle = Mathf.Atan2(headPosition.y - mousePosition.y, headPosition.x - mousePosition.x) *
                      Mathf.Rad2Deg;
        if (Math.Abs(angle) <= 70)
            transform.rotation = Quaternion.Euler(new Vector3(0f, 0f, -angle));
    }
}